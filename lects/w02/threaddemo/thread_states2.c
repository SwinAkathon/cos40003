// improve from thread_states1.c such that each thread displays its process ID (PID) and its current process state while running. 
// We'll use system calls to get the PID and read from the /proc filesystem to 
// display the state. The /proc/[pid]/status file contains the status of the 
// process, which can be read to display the current state.
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <sys/types.h>
#include <string.h>

// Function to read and display the current process state
void display_process_state() {
    char path[40], line[100], *p;
    FILE* statusf;
    pid_t pid = getpid(); // Get the process ID

    // Construct the path to the /proc/[pid]/status file
    snprintf(path, 40, "/proc/%d/status", pid);

    // Open the status file for reading
    statusf = fopen(path, "r");
    if (!statusf) {
        perror("Failed to open process status file");
        return;
    }

    // Read the status file line by line
    while (fgets(line, 100, statusf)) {
        // Find the line containing the "State" information
        if (strncmp(line, "State:", 6) == 0) {
            // Print the process ID and its state
            printf("Process ID: %d, %s", pid, line);
            break;
        }
    }

    fclose(statusf); // Close the status file
}

// Function that represents a running thread
void* running_thread(void* arg) {
    int id = *(int*)arg;
    printf("Thread %d is running.\n", id);

    // Simulate running state indefinitely and display the process state
    while (1) {
        display_process_state();
        sleep(2); // Sleep for a while to make the output readable
    }
    return NULL;
}

// Function that represents a sleeping (blocked) thread
void* sleeping_thread(void* arg) {
    int id = *(int*)arg;
    printf("Thread %d is sleeping.\n", id);

    // Display the process state before sleeping
    display_process_state();
    sleep(10); // Sleep for 10 seconds to simulate blocked state

    // Display the process state after waking up
    display_process_state();
    printf("Thread %d woke up and is terminating.\n", id);
    return NULL;
}

// Function that represents a thread that terminates immediately
void* terminating_thread(void* arg) {
    int id = *(int*)arg;
    printf("Thread %d is terminating immediately.\n", id);

    // Display the process state before terminating
    display_process_state();
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
