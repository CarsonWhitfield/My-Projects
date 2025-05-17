//
// Created by carson on 11/15/21.
//

#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "assembler.h"

char *ASM_ERROR_UNKNOWN_INSTRUCTION = "Unknown Assembly Instruction";
char *ASM_ERROR_ARG_REQUIRED = "Argument Required";
char *ASM_ERROR_BAD_LABEL = "Bad Label";
char *ASM_ERROR_OUT_OF_RANGE = "Number is out of range";

//=========================================================
//  All the instructions available on the LMSM architecture
//=========================================================
const char *INSTRUCTIONS[28] =
        {"ADD", "SUB", "LDA", "STA", "BRA", "BRZ", "BRP", "INP", "OUT", "HLT", "COB", "DAT",
         "LDI",
         "JAL", "CALL", "RET",
         "SPUSH", "SPUSHI", "SPOP", "SDUP", "SDROP", "SSWAP", "SADD", "SSUB", "SMAX", "SMIN", "SMUL", "SDIV"
        };
const int INSTRUCTION_COUNT = 28;

//===================================================================
//  All the instructions that require an arg on the LMSM architecture
//===================================================================
const char *ARG_INSTRUCTIONS[11] =
        {"ADD", "SUB", "LDA", "STA", "BRA", "BRZ", "BRP", "DAT",
         "LDI",
         "CALL",
         "SPUSHI"
        };
const int ARG_INSTRUCTION_COUNT = 11;

//======================================================
// Constructors/Destructors
//======================================================

asm_instruction * asm_make_instruction(char* type, char *label, char *label_reference, int value, asm_instruction * predecessor) {
    asm_instruction *new_instruction = calloc(1, sizeof(asm_instruction));
    new_instruction->instruction = type;
    new_instruction->label = label;
    new_instruction->label_reference = label_reference;
    new_instruction->value = value;
    new_instruction->next = NULL;

    new_instruction->slots = 1;

    if (predecessor != NULL) {
        predecessor->next = new_instruction;
        new_instruction->offset = predecessor->offset + predecessor->slots;
    } else {
        new_instruction->offset = 0;
    }

    // Set the number of slots for the instruction
    if (strcmp(type, "SPUSHI") == 0) {
        new_instruction->slots = 2; // SPUSHI occupies two slots
    } else if (strcmp(type, "CALL") == 0 || strcmp(type, "JAL") == 0) {
        new_instruction->slots = 3; // CALL and JAL occupy three slots
    } else if (strcmp(type, "ADD") == 0 || strcmp(type, "SUB") == 0 || strcmp(type, "LDA") == 0 ||
               strcmp(type, "STA") == 0 || strcmp(type, "BRA") == 0 || strcmp(type, "BRZ") == 0 ||
               strcmp(type, "BRP") == 0 || strcmp(type, "LDI") == 0 || strcmp(type, "CALL") == 0 ||
               strcmp(type, "SPUSH") == 0 || strcmp(type, "SPOP") == 0 || strcmp(type, "SDUP") == 0 ||
               strcmp(type, "SDROP") == 0 || strcmp(type, "SSWAP") == 0 || strcmp(type, "SADD") == 0 ||
               strcmp(type, "SSUB") == 0 || strcmp(type, "SMUL") == 0 || strcmp(type, "SDIV") == 0 ||
               strcmp(type, "SMAX") == 0 || strcmp(type, "SMIN") == 0 || strcmp(type, "RET") == 0 ||
               strcmp(type, "INP") == 0 || strcmp(type, "OUT") == 0 || strcmp(type, "HLT") == 0 ||
               strcmp(type, "COB") == 0 || strcmp(type, "DAT") == 0) {
        new_instruction->slots = 1;
               }

    return new_instruction;
}


void asm_delete_instruction(asm_instruction *instruction) {
    if (instruction == NULL) {
        return;
    }
    asm_delete_instruction(instruction->next);
    free(instruction);
}

asm_compilation_result * asm_make_compilation_result() {
    asm_compilation_result *result = calloc(1, sizeof(asm_compilation_result));
    return result;
}

void asm_delete_compilation_result(asm_compilation_result *result) {
    asm_delete_instruction(result->root);
    free(result);
}

//======================================================
// Helpers
//======================================================
int asm_is_instruction(char * token) {
    for (int i = 0; i < INSTRUCTION_COUNT; ++i) {
        if (strcmp(token, INSTRUCTIONS[i]) == 0) {
            return 1;
        }
    }
    return 0;
}

int asm_instruction_requires_arg(char * token) {
    for (int i = 0; i < ARG_INSTRUCTION_COUNT; ++i) {
        if (strcmp(token, ARG_INSTRUCTIONS[i]) == 0) {
            return 1;
        }
    }
    return 0;
}

int asm_is_num(char * token){
    if (*token == '-') { // allow a leading negative
        token++;
        if (*token == '\0') {
            return 0;
        }
    }
    while (*token != '\0') {
        if (*token < '0' || '9' < *token) {
            return 0;
        }
        token++;
    }
    return 1;
}

int asm_find_label(asm_instruction *root, char *label) {

    asm_instruction *current = root;

    while (current != NULL)
    {
        // Check if the current instruction's label matches the target label
        if(current->label != NULL && strcmp(current->label, label) == 0)
        {
            return current->offset; // Return the offset if the label is found
        }
        current = current->next; // Move to the next instruction
    }
    // Return -1 if the label is not found
    return -1;
}


//======================================================
// Assembly Parsing/Scanning
//======================================================

void asm_parse_src(asm_compilation_result *result, char *original_src) {
    result->error = NULL;
    char *src = calloc(strlen(original_src) + 1, sizeof(char));
    strcpy(src, original_src);

    asm_instruction *last_instruction = NULL;
    asm_instruction *predecessor = NULL;
    char *current_str = strtok(src, " \n");

    printf("Starting parsing process...\n");

    while(current_str !=NULL){
        char *type = NULL;
        char *label = NULL;
        char *label_reference = NULL;
        int value = 0;

        if(asm_is_instruction(current_str)){
            type = current_str;
            current_str = strtok(NULL," \n");
        }else{
            label = current_str;
            current_str = strtok(NULL," \n");

            if(!asm_is_instruction(current_str)){


                result->error = ASM_ERROR_UNKNOWN_INSTRUCTION;
                break;
            }else{

                type = current_str;
                current_str =strtok(NULL," \n");
            }
        }
        if(asm_instruction_requires_arg(type)){
            if(current_str==NULL){
                result->error = ASM_ERROR_ARG_REQUIRED;
                return;
            }else{
                if(asm_is_num(current_str)){
                    value= atoi(current_str);
                    if(value>999 || value<-999){
                        result->error = ASM_ERROR_OUT_OF_RANGE;
                        break;
                    }else{current_str = strtok(NULL," \n");}
                }else{
                    label_reference = current_str;
                    current_str= strtok(NULL," \n");
                }
            }
        }

        asm_instruction * new_instruction =asm_make_instruction(type,label,label_reference,value,predecessor);
        if(result->root==NULL){
            result->root = new_instruction;
        }
        predecessor = new_instruction;
    }

}

//======================================================
// Machine Code Generation
//======================================================

void asm_gen_code_for_instruction(asm_compilation_result  * result, asm_instruction *instruction) {

    //
    // note that some instructions will take up multiple slots
    //
    // note that if the instruction has a label reference rather than a raw number reference
    // you will need to look it up with `asm_find_label` and, if the label does not exist,
    // report the error as ASM_ERROR_BAD_LABEL

    int value_for_instruction = instruction->value;
    if (instruction->label_reference) {
        value_for_instruction = asm_find_label(result->root, instruction->label_reference);

        if (value_for_instruction == -1)
        {
            result->error = ASM_ERROR_BAD_LABEL;
            return;
        }
    }

    if (strcmp("ADD", instruction->instruction) == 0) {
        result->code[instruction->offset] = 100 + value_for_instruction;
    } else if (strcmp("SUB", instruction->instruction) == 0) {
        result->code[instruction->offset] = 200 + value_for_instruction;
    } else if (strcmp("STA", instruction->instruction) == 0) {
        result->code[instruction->offset] = 300 + value_for_instruction;
    } else if (strcmp("LDI", instruction->instruction) == 0) {
        result->code[instruction->offset] = 400 + value_for_instruction;
    } else if (strcmp("LDA", instruction->instruction) == 0) {
        result->code[instruction->offset] = 500 + value_for_instruction;
    } else if (strcmp("BRA", instruction->instruction) == 0) {
        result->code[instruction->offset] = 600 + value_for_instruction;
    } else if (strcmp("BRZ", instruction->instruction) == 0) {
        result->code[instruction->offset] = 700 + value_for_instruction;
    } else if (strcmp("BRP", instruction->instruction) == 0) {
        result->code[instruction->offset] = 800 + value_for_instruction;
    } else if (strcmp("INP", instruction->instruction) == 0) {
        result->code[instruction->offset] = 901;
    } else if (strcmp("OUT", instruction->instruction) == 0) {
        result->code[instruction->offset] = 902;
    } else if (strcmp("HLT", instruction->instruction) == 0) {
        result->code[instruction->offset] = 0;
    } else if (strcmp("COB", instruction->instruction) == 0) {
        result->code[instruction->offset] = 0;
    } else if (strcmp("DAT", instruction->instruction) == 0) {
        result->code[instruction->offset] = instruction->value;
    } else if (strcmp("SPUSH", instruction->instruction) == 0) {
        result->code[instruction->offset] = 920;
    } else if (strcmp("SPUSHI", instruction->instruction) == 0) {
        result->code[instruction->offset] = 401;  // 401 for SPUSHI
        result->code[instruction->offset + 1] = 920; // 920 for SPUSH
        instruction->slots = 2; // SPUSHI occupies two slots
    } else if (strcmp("SPOP", instruction->instruction) == 0) {
        result->code[instruction->offset] = 921;
    } else if (strcmp("SDUP", instruction->instruction) == 0) {
        result->code[instruction->offset] = 922;
    } else if (strcmp("SDROP", instruction->instruction) == 0) {
        result->code[instruction->offset] = 923;
    } else if (strcmp("SSWAP", instruction->instruction) == 0) {
        result->code[instruction->offset] = 924;
    } else if (strcmp("SADD", instruction->instruction) == 0) {
        result->code[instruction->offset] = 930;
    } else if (strcmp("SSUB", instruction->instruction) == 0) {
        result->code[instruction->offset] = 931;
    } else if (strcmp("SMUL", instruction->instruction) == 0) {
        result->code[instruction->offset] = 932;
    } else if (strcmp("SDIV", instruction->instruction) == 0) {
        result->code[instruction->offset] = 933;
    } else if (strcmp("SMAX", instruction->instruction) == 0) {
        result->code[instruction->offset] = 934;
    } else if (strcmp("SMIN", instruction->instruction) == 0) {
        result->code[instruction->offset] = 935;
    } else if (strcmp("CALL", instruction->instruction) == 0) {
        result->code[instruction->offset] = 401;
        result->code[instruction->offset + 1] = 920;
        result->code[instruction->offset + 2] = 910;
        instruction->slots = 3;
    } else if (strcmp("JAL", instruction->instruction) == 0) {
         result->code[instruction->offset] = 401; // Save return address
         result->code[instruction->offset + 1] = 920; // Push return address
         result->code[instruction->offset + 2] = 910; // Jump to target
         instruction->slots = 3; // JAL uses 3 slots

    } else if (strcmp("RET", instruction->instruction) == 0) {
        result->code[instruction->offset] = 911;
    } else {
        // If the instruction is not recognized, report an error
        result->error = ASM_ERROR_UNKNOWN_INSTRUCTION;
    }

    // Default slot allocation for single-slot instructions
    if (instruction->slots == 0) {
        instruction->slots = 1;
    }
}

void asm_gen_code(asm_compilation_result * result) {
    asm_instruction * current = result->root;
    while (current != NULL) {
        asm_gen_code_for_instruction(result, current);
        current = current->next;
    }
}

//======================================================
// Main API
//======================================================

asm_compilation_result * asm_assemble(char *src) {
    asm_compilation_result * result = asm_make_compilation_result();
    asm_parse_src(result, src);
    asm_gen_code(result);
    return result;
}
