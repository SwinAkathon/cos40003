#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

// Function that represents a running thread
void *running_thread(void *arg) {
    printf("Thread %d is running.\n", *(int *)arg);
    while (1); // Keep running indefinitely to stay in Running state
    return NULL;
}

// Function that represents a sleeping (blocked) thread
void *sleeping_thread(void *arg) {
    printf("Thread %d is sleeping.\n", *(int *)arg);
    sleep(10); // Sleep for 10 seconds to simulate blocked state
    printf("Thread %d woke up and is terminating.\n", *(int *)arg);
    return NULL;
}

// Function that represents a thread that terminates immediately
void *terminating_thread(void *arg) {
    printf("Thread %d is terminating immediately.\n", *(int *)arg);
    return NULL;
}

int main() {
    pthread_t threads[3];
    int thread_ids[3] = {1, 2, 3};

    // Create a thread that will run indefinitely
    if (pthread_create(&threads[0], NULL, running_thread, &thread_ids[0]) != 0) {
        perror("Failed to create thread 1");
    }

    // Create a thread that will sleep for a while and then terminate
    if (pthread_create(&threads[1], NULL, sleeping_thread, &thread_ids[1]) != 0) {
        perror("Failed to create thread 2");
    }

    // Create a thread that will terminate immediately
    if (pthread_create(&threads[2], NULL, terminating_thread, &thread_ids[2]) != 0) {
        perror("Failed to create thread 3");
    }

    // Wait for the sleeping and terminating threads to finish
    pthread_join(threads[1], NULL);
    pthread_join(threads[2], NULL);

    // The running thread will continue indefinitely; we can sleep in the main thread
    sleep(20); // Give enough time to observe the states

    // Terminate the program
    printf("Main thread is exiting.\n");
    return 0;
}