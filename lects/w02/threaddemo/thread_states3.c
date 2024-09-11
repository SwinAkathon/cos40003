#define _GNU_SOURCE // Required for syscall definitions
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/syscall.h> // Include for syscall function
#include <string.h>

// Global constants for sleeping times
const int RUNNING_THREAD_SLEEP_TIME = 500000; // Sleep time in microseconds (0.5 seconds)
const int SLEEPING_THREAD_SLEEP_TIME = 10;    // Sleep time in seconds
const int INITIAL_BLOCKED_WAIT_TIME = 30;      // Initial wait time for blocked state in seconds
const int MAIN_THREAD_WAIT_TIME = 5;          // Main thread wait time in seconds

// Mutex for simulating blocked state
pthread_mutex_t mutex;

// Function to display the current thread ID and process state
void display_thread_info(const int id, const char* state) {

    // thread id assigned by pthread 
    // const int tid = pthread_self();

    // system thread id assigned by os
    const long int stid = syscall(SYS_gettid);
    
    printf("Thread %d PID: %d; TID: %ld - Process State: %s\n", id, getpid(), stid, state);
    fflush(stdout); // Ensure immediate output
}

// Function that represents a running thread
void* running_thread(void* arg) {
    int id = *(int*)arg;
    // printf("Thread %d is running.\n", id);
    // display_thread_info(id, "Running");

    // Simulate running state indefinitely
    while (1) {
        display_thread_info(id, "Running");
        usleep(RUNNING_THREAD_SLEEP_TIME); // Sleep for 0.5 seconds to make the output readable
    }
    return NULL;
}

// Function that represents a sleeping (blocked) thread
void* sleeping_thread(void* arg) {
    int id = *(int*)arg;
    // printf("Thread %d is sleeping.\n", id);

    // Simulate sleeping state
    display_thread_info(id, "Sleeping");
    sleep(SLEEPING_THREAD_SLEEP_TIME); // Sleep for 10 seconds to simulate blocked state
    display_thread_info(id, "Awake");

    printf("Thread %d woke up and is terminating.\n", id);
    return NULL;
}

// Function that represents a thread in blocked state
void* blocked_thread(void* arg) {
    int id = *(int*)arg;
    printf("Thread %d is trying to acquire a lock.\n", id);

    // Display blocked state and lock mutex
    display_thread_info(id, "Blocked");

    pthread_mutex_lock(&mutex);

    // printf("Thread %d: %ld, acquired the lock and is now terminating.\n", id, pthread_self());

    display_thread_info(id, "Lock acquired (terminating)");

    pthread_mutex_unlock(&mutex);
    return NULL;
}

int main() {
    pthread_t threads[3];
    int thread_ids[3] = {1, 2, 3};

    // Initialize the mutex
    pthread_mutex_init(&mutex, NULL);

    // Lock the mutex to make the blocked thread wait
    pthread_mutex_lock(&mutex);

    // Create a thread that will run indefinitely
    if (pthread_create(&threads[0], NULL, running_thread, &thread_ids[0]) != 0) {
        perror("Failed to create thread 1");
    }

    // Create a thread that will sleep for a while and then terminate
    if (pthread_create(&threads[1], NULL, sleeping_thread, &thread_ids[1]) != 0) {
        perror("Failed to create thread 2");
    }

    // Create a thread that will be blocked
    if (pthread_create(&threads[2], NULL, blocked_thread, &thread_ids[2]) != 0) {
        perror("Failed to create thread 3");
    }

    // Allow some time for threads to demonstrate their states
    sleep(INITIAL_BLOCKED_WAIT_TIME);

    // Release the mutex to allow the blocked thread to proceed
    pthread_mutex_unlock(&mutex);

    // Wait for the sleeping and blocked threads to finish
    pthread_join(threads[1], NULL);
    pthread_join(threads[2], NULL);

    // The running thread will continue indefinitely; we can sleep in the main thread
    sleep(MAIN_THREAD_WAIT_TIME); // Give enough time to observe the states

    // Terminate the program
    printf("Main thread is exiting.\n");
    return 0;
}
