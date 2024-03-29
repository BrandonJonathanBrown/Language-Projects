ORG  0x7C00                  ; Set the origin, standard for boot sectors
BITS 16                      ; We're operating in 16-bit real mode

start:
    xor ax, ax               ; Clear AX register to use AX for setting DS segment
    mov ds, ax               ; Initialize DS to 0
    mov dh, 0x00             ; Set head number to 0
    mov cx, 0x0002           ; Set cylinder and sector (CH = track, CL = sector)
    mov bx, 0x8000           ; Set buffer address for disk read (ES:BX). Choose a safe location.
    mov es, bx               ; ES = 0x8000. ES needs segment address, BX is reused for offset.
    xor bx, bx               ; BX = 0, offset for buffer address
    mov ax, 0x0201           ; AH = 0x02 for read operation, AL = 0x01 for number of sectors to read
    mov dl, 0x00             ; Drive number (0 = floppy, 0x80 = first hard disk)
    int 0x13                 ; Call interrupt 0x13, disk service
    jc  ERR                  ; Jump to ERR label if carry flag is set, indicating an error
    jmp 0x8000:0x0000        ; Jump to the code loaded in memory at 0x8000:0x0000

ERR:
    mov si, errorMsg         ; Load the address of errorMsg into SI
    call PrintString         ; Call the subroutine to print the error message

haltLoop:
    cli                      ; Clear interrupts
    hlt                      ; Halt the CPU
    jmp haltLoop             ; Infinite loop in case 'hlt' instruction is skipped

PrintString:                 ; Subroutine to print strings stored in SI register
    lodsb                    ; Load string byte at DS:SI into AL, increment SI
    or   al, al              ; Logical OR AL by itself to set the zero flag if AL is 0
    jz   .done               ; Jump if zero flag is set (if AL was 0, indicating end of string)
    mov  ah, 0x0E            ; Function number for teletype output
    int  0x10                ; BIOS interrupt for video services
    jmp  PrintString         ; Loop to print next character
.done:
    ret                      ; Return from subroutine

errorMsg db 'Disk read error! System halted.', 0

times 510 - ($ - $$) db 0    ; Pad the boot sector to 510 bytes with 0
dw 0xAA55                    ; Boot signature at the end of the boot sector
