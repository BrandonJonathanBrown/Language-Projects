org 0x100            ; Origin, specifying where the program will be loaded in memory
bits 16              ; We're working in 16-bit real mode

start:
    mov ax, 0x3       ; Set video mode function
    int 0x10          ; Call BIOS video interrupt
    call PrintMsg     ; Print welcome message
    
GameLoop:
    call PrintMsg     ; Prompt for guess
    mov ah, 0x00      ; BIOS keyboard service - wait for keypress
    int 0x16          ; Call BIOS interrupt to read character
    
    ; Convert ASCII to integer by subtracting '0'
    sub al, '0'
    
    ; For simplicity, we simulate a random number generation
    ; by getting the low byte of the timer, which changes rapidly
    mov ah, 0x00
    int 0x1A          ; Call BIOS time service
    and dl, 0x0F      ; Ensure the "random number" is between 0 and 9
    
    ; Compare input with "random" number
    cmp al, dl
    je GameWon
    jmp GameLost
    
GameWon:
    mov si, won
    call PrintMsg
    jmp Exit

GameLost:
    mov si, loss
    call PrintMsg
    jmp Exit

PrintMsg:
    mov ah, 0x0E      ; BIOS teletype service
    
PrintLoop:
    lodsb             ; Load string byte at DS:SI into AL, increment SI
    test al, al       ; Test if AL is 0 (end of string)
    jz  Return        ; If it's the end of the string, return
    int 0x10          ; Otherwise, print character in AL
    jmp PrintLoop     ; Loop to print next character

Return:
    ret
    
Exit:
    mov ax, 0x4C00    ; Terminate program
    int 0x21
    
msg   db 'Welcome To My Guessing Game', 13, 10
      db 'Pick A Number Between 0 - 9: ', 0
won   db 'You Guessed The Correct Number!', 13, 10, 0
loss  db 'You Guessed The Incorrect Number!', 13, 10, 0

