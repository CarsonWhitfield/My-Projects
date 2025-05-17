#include "lmsm.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#define MEMORY_SIZE 200


//======================================================
//  Utilities
//======================================================

void lmsm_cap_value(int * val){
    if (*val > 999) {
        *val = 999;  // Cap the value at 999
    } else if (*val < -999) {
        *val = -999;  // Cap the value at -999
    }
}

int lmsm_has_two_values_on_stack(lmsm *our_little_machine) {
    return (our_little_machine->stack_pointer < (TOP_OF_MEMORY - 2));
}

//======================================================
//  Instruction Implementation
//======================================================

void lmsm_i_jal(lmsm *our_little_machine) {
    // Save the current program counter to the return address pointer memory location
    our_little_machine->return_address_pointer++;
    our_little_machine->memory[our_little_machine->return_address_pointer] = our_little_machine->program_counter;

    // Update the program counter with the value from the accumulator
    our_little_machine->program_counter = our_little_machine->accumulator;

    // Consume the top of the stack (increment stack pointer)
    our_little_machine->stack_pointer++;
}

void lmsm_i_ret(lmsm *our_little_machine) {
    // Check if return address pointer is valid
    if (our_little_machine->return_address_pointer == 0) {
        return; // Handle error appropriately, e.g., by exiting or halting
    }

    // Retrieve the return address from memory
    our_little_machine->program_counter = our_little_machine->memory[our_little_machine->return_address_pointer];

    // Decrement the return address pointer to point to the previous address for the next return
    our_little_machine->return_address_pointer--;

}

void lmsm_i_push(lmsm *our_little_machine) {
    our_little_machine->stack_pointer--;
    our_little_machine->memory[our_little_machine->stack_pointer] = our_little_machine->accumulator;
}

void lmsm_i_pop(lmsm *our_little_machine) {
    if(our_little_machine->stack_pointer >= (sizeof(our_little_machine->memory)/sizeof(our_little_machine->memory[0])))
    {
        our_little_machine->status = STATUS_HALTED;
        our_little_machine->error_code = ERROR_BAD_STACK;
    }else
    {
        our_little_machine->accumulator = our_little_machine->memory[our_little_machine->stack_pointer];
        our_little_machine->stack_pointer++;
    }
}

void lmsm_i_dup(lmsm *our_little_machine) {
    int top_value = our_little_machine->memory[our_little_machine->stack_pointer];
    our_little_machine->stack_pointer--;
    our_little_machine->memory[our_little_machine->stack_pointer] = top_value;
}

void lmsm_i_drop(lmsm *our_little_machine) {
    if (our_little_machine->stack_pointer >= (sizeof(our_little_machine->memory)/sizeof(our_little_machine->memory[0])))
    {
        our_little_machine->status = STATUS_HALTED;
        our_little_machine->error_code = ERROR_BAD_STACK;
    }else
    {
        our_little_machine->stack_pointer++;
    }
}

void lmsm_i_swap(lmsm *our_little_machine) {
    if (our_little_machine->stack_pointer < (sizeof(our_little_machine->memory) / sizeof(our_little_machine->memory[0])) - 1)
    {
        int temp = our_little_machine->memory[our_little_machine->stack_pointer];
        our_little_machine->memory[our_little_machine->stack_pointer] = our_little_machine->memory[our_little_machine->stack_pointer + 1];
        our_little_machine->memory[our_little_machine->stack_pointer + 1] = temp;
    }else
    {
        our_little_machine->status = STATUS_HALTED;
        our_little_machine->error_code = ERROR_BAD_STACK;
    }
}

void lmsm_i_sadd(lmsm *our_little_machine) {

        int a = our_little_machine->memory[our_little_machine->stack_pointer];
        our_little_machine->stack_pointer++;
        int b = our_little_machine->memory[our_little_machine->stack_pointer];
        our_little_machine->memory[our_little_machine->stack_pointer] = a + b;
        lmsm_cap_value(&our_little_machine->memory[our_little_machine->stack_pointer]);

}

void lmsm_i_ssub(lmsm *our_little_machine) {
    int a = our_little_machine->memory[our_little_machine->stack_pointer];

    our_little_machine->stack_pointer++;

    int b = our_little_machine->memory[our_little_machine->stack_pointer];

    int result = b - a; // Perform the subtraction
    our_little_machine->memory[our_little_machine->stack_pointer] = result;

    lmsm_cap_value(&our_little_machine->memory[our_little_machine->stack_pointer]);
}




void lmsm_i_smax(lmsm *our_little_machine) {
    // Check if there are at least two elements on the stack
    if (our_little_machine->stack_pointer >= 199) {
        // Not enough elements on the stack
        our_little_machine->status = STATUS_HALTED;
        our_little_machine->error_code = ERROR_BAD_STACK;
        return;
    }

    // Retrieve value b (top of stack)
    int b = our_little_machine->memory[our_little_machine->stack_pointer];

    // Move stack pointer up to the next value a
    our_little_machine->stack_pointer++;
    int a = our_little_machine->memory[our_little_machine->stack_pointer];

    // Store the maximum of a and b back at the current stack pointer location
    our_little_machine->memory[our_little_machine->stack_pointer] = (a > b) ? a : b;

    // Adjust stack pointer to reflect the new top of the stack
    our_little_machine->stack_pointer;
}

void lmsm_i_smin(lmsm *our_little_machine) {
    // Check if there are at least two elements on the stack
    if (our_little_machine->stack_pointer >= 199) {
        // Not enough elements on the stack
        our_little_machine->status = STATUS_HALTED;
        our_little_machine->error_code = ERROR_BAD_STACK;
        return;
    }

    // Retrieve value b (top of stack)
    int b = our_little_machine->memory[our_little_machine->stack_pointer];

    // Move stack pointer up to the next value a
    our_little_machine->stack_pointer++;
    int a = our_little_machine->memory[our_little_machine->stack_pointer];

    // Store the maximum of a and b back at the current stack pointer location
    our_little_machine->memory[our_little_machine->stack_pointer] = (a < b) ? a : b;

    // Adjust stack pointer to reflect the new top of the stack
    our_little_machine->stack_pointer;
}

void lmsm_i_smul(lmsm *our_little_machine) {
    if (our_little_machine->stack_pointer >= 199) {
        // Not enough elements on the stack
        our_little_machine->status = STATUS_HALTED;
        our_little_machine->error_code = ERROR_BAD_STACK;
        return;
    }
        int a = our_little_machine->memory[our_little_machine->stack_pointer];
        our_little_machine->stack_pointer++;
        int b = our_little_machine->memory[our_little_machine->stack_pointer];
        our_little_machine->memory[our_little_machine->stack_pointer] = a * b;
        lmsm_cap_value(&our_little_machine->memory[our_little_machine->stack_pointer]);

}

void lmsm_i_sdiv(lmsm *our_little_machine) {
    // Retrieve value a (numerator) from the stack
    int a = our_little_machine->memory[our_little_machine->stack_pointer];

    // Move the stack pointer up to the next value (denominator)
    our_little_machine->stack_pointer++;

    // Retrieve value b (denominator) from the stack
    int b = our_little_machine->memory[our_little_machine->stack_pointer];

    // Check if the denominator is zero
    if (b != 0) {
        // Perform the division if b is not zero
        our_little_machine->memory[our_little_machine->stack_pointer] = b / a;
    } else {
        // If b is zero, set the error state
        our_little_machine->error_code = ERROR_BAD_STACK;
        our_little_machine->status = STATUS_HALTED;
    }
}

void lmsm_i_out(lmsm *our_little_machine) {
    char buffer[20];
    snprintf(buffer, sizeof(buffer), "%d", our_little_machine->accumulator);
    strcat(our_little_machine->output_buffer, buffer);
    strcat(our_little_machine->output_buffer, " ");
}

void lmsm_i_inp(lmsm *our_little_machine) {
    char input[20];
    fgets(input, sizeof(input), stdin);
    our_little_machine->accumulator = atoi(input);
    lmsm_cap_value(&our_little_machine->accumulator);
}

void lmsm_i_load(lmsm *our_little_machine, int location) {
our_little_machine->accumulator = our_little_machine->memory[location];
}

void lmsm_i_add(lmsm *our_little_machine, int location) {
    our_little_machine->accumulator += our_little_machine->memory[location];
}

void lmsm_i_sub(lmsm *our_little_machine, int location) {
    our_little_machine->accumulator -= our_little_machine->memory[location];
}

void lmsm_i_load_immediate(lmsm *our_little_machine, int value) {
    our_little_machine->accumulator = value;
    lmsm_cap_value(&our_little_machine->accumulator);
}

void lmsm_i_store(lmsm *our_little_machine, int location) {
    our_little_machine->memory[location] = our_little_machine->accumulator;
}

void lmsm_i_halt(lmsm *our_little_machine) {
    our_little_machine->status = STATUS_HALTED;
}

void lmsm_i_branch_unconditional(lmsm *our_little_machine, int location) {
    our_little_machine->program_counter = location;
}

void lmsm_i_branch_if_zero(lmsm *our_little_machine, int location) {
    if(our_little_machine->accumulator == 0){
        our_little_machine->program_counter = location;
    }
}

void lmsm_i_branch_if_positive(lmsm *our_little_machine, int location) {
    if (our_little_machine->accumulator >= 0) {
        our_little_machine->program_counter = location;
    }
}

void lmsm_step(lmsm *our_little_machine) {
    if (our_little_machine->status != STATUS_HALTED) {
        int next_instruction = our_little_machine->memory[our_little_machine->program_counter];
        our_little_machine->program_counter++;
        our_little_machine->current_instruction = next_instruction;
        int instruction = our_little_machine->current_instruction;
        lmsm_exec_instruction(our_little_machine, instruction);
    }
}

//======================================================
//  LMSM Implementation
//======================================================

void lmsm_exec_instruction(lmsm *our_little_machine, int instruction) {
    if (instruction == 0) {
        lmsm_i_halt(our_little_machine);
    } else if (100 <= instruction && instruction <= 199) {
        lmsm_i_add(our_little_machine, instruction - 100);
    } else if (200 <= instruction && instruction <= 299) {
        lmsm_i_sub(our_little_machine, instruction - 200);
    }else if (300 <= instruction && instruction <= 399){
        lmsm_i_store(our_little_machine, instruction - 300);
    }else if (400 <= instruction && instruction <= 499){
        lmsm_i_load_immediate(our_little_machine, instruction - 400);
    }else if (500 <= instruction && instruction <= 599){
        lmsm_i_load(our_little_machine, instruction - 500);
    }else if (600 <= instruction && instruction <= 699) {
        lmsm_i_branch_unconditional(our_little_machine, instruction - 600);
    }else if (700 <= instruction && instruction <= 799){
        lmsm_i_branch_if_zero(our_little_machine, instruction - 700);
    }else if (800 <= instruction && instruction <= 899){
        if (instruction == 820){
            lmsm_i_branch_if_positive(our_little_machine, instruction - 800);
        }
    }else if (instruction == 901){
        lmsm_i_inp(our_little_machine);
    }else if (instruction == 902){
        lmsm_i_out(our_little_machine);
    }else if (instruction == 910){
        lmsm_i_jal(our_little_machine);
    }else if (instruction == 911){
        lmsm_i_ret(our_little_machine);
    }else if (instruction == 920) {
        lmsm_i_push(our_little_machine);
    }else if (instruction == 921){
        lmsm_i_pop(our_little_machine);
    }else if (instruction == 922){
        lmsm_i_dup(our_little_machine);
    }else if (instruction == 923){
        lmsm_i_drop(our_little_machine);
    } else if (instruction == 924){
        lmsm_i_swap(our_little_machine);
    }else if (instruction == 930) {
        lmsm_i_sadd(our_little_machine);
    }else if (instruction == 931){
        lmsm_i_ssub(our_little_machine);
    }else if (instruction == 932){
        lmsm_i_smul(our_little_machine);
    }else if (instruction == 933){
        lmsm_i_sdiv(our_little_machine);
    }else if (instruction == 934){
        lmsm_i_smax(our_little_machine);
    }else if (instruction == 935){
        lmsm_i_smin(our_little_machine);
    }else {
        our_little_machine->error_code = ERROR_UNKNOWN_INSTRUCTION;
        our_little_machine->status = STATUS_HALTED;
    }
    lmsm_cap_value(&our_little_machine->accumulator);
}

void lmsm_load(lmsm *our_little_machine, int *program, int length) {
    for (int i = 0; i < length; ++i) {
        our_little_machine->memory[i] = program[i];
    }
}

void lmsm_init(lmsm *the_machine) {
    the_machine->accumulator = 0;
    the_machine->status = STATUS_READY;
    the_machine->error_code = ERROR_NONE;
    the_machine->program_counter = 0;
    the_machine->current_instruction = 0;
    the_machine->stack_pointer = TOP_OF_MEMORY + 1;
    the_machine->return_address_pointer = TOP_OF_MEMORY - 100;
    memset(the_machine->output_buffer, 0, sizeof(char) * 1000);
    memset(the_machine->memory, 0, sizeof(int) * TOP_OF_MEMORY + 1);
}

void lmsm_reset(lmsm *our_little_machine) {
    lmsm_init(our_little_machine);
}

void lmsm_run(lmsm *our_little_machine) {
    our_little_machine->status = STATUS_RUNNING;
    while (our_little_machine->status != STATUS_HALTED) {
        lmsm_step(our_little_machine);
    }
}

lmsm *lmsm_create() {
    lmsm *the_machine = malloc(sizeof(lmsm));
    lmsm_init(the_machine);
    return the_machine;
}

void lmsm_delete(lmsm *the_machine){
    free(the_machine);
}