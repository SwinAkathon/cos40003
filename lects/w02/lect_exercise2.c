#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> // For fork(), getpid()

// Define a structure for a linked list node to store parent-child relationships
typedef struct ProcessNode {
    pid_t parent_pid;
    pid_t child_pid;
    struct ProcessNode* next;
} ProcessNode;

// Head pointer for the linked list
ProcessNode* head = NULL;

// Function to add a new relationship to the linked list
void add_relationship(pid_t parent, pid_t child) {
    ProcessNode* new_node = (ProcessNode*)malloc(sizeof(ProcessNode));
    new_node->parent_pid = parent;
    new_node->child_pid = child;
    new_node->next = head;
    head = new_node;
}

// Function to display the relationships in a tree-like structure
void display_tree() {
    ProcessNode* current;
    pid_t root_pid = getpid();
    printf("Process Tree (PID relationships):\n");

    // Print the root process
    printf("Parent PID: %d\n", root_pid);

    // Traverse the linked list to display each parent-child relationship
    for (current = head; current != NULL; current = current->next) {
        // printf("Parent PID: %d -> Child PID: %d\n", current->parent_pid, current->child_pid);
        printf("Parent PID: %d -> Child PID: %d\n", current->parent_pid, current->child_pid);
    }
}

// Main function
int main() {
    pid_t pid1, pid2, pid3;
    pid_t parent_pid = getpid(); // The original process's PID

    printf("my pid is %d\n", parent_pid);

    // First fork
    pid1 = fork();
    if (pid1 > 0) { // Parent process
        add_relationship(parent_pid, pid1);
    } else if (pid1 == 0) { // Child process
        parent_pid = getpid();
    }

    // Second fork
    pid2 = fork();
    if (pid2 > 0) { // Parent process
        add_relationship(parent_pid, pid2);
    } else if (pid2 == 0) { // Child process
        parent_pid = getpid();
    }

    // Third fork
    pid3 = fork();
    if (pid3 > 0) { // Parent process
        add_relationship(parent_pid, pid3);
    } else if (pid3 == 0) { // Child process
        parent_pid = getpid();
    }

    printf("my pid is %d\n", getpid());

    // Wait for all processes to finish and display the tree only from the original process
    sleep(1); // Wait for child processes to complete
    if (getpid() == parent_pid) {
        display_tree();
    }

    return 0;
}
