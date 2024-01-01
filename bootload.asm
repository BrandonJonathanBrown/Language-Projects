ORG   0x7C00 ;Author Brandon Jonathan Brown
BITS  16

; IN (dl)
mov dh, 0x00     
mov cx, 0x0002
mov bx, 0x1000
mov es, bx
xor bx, bx
mov ax, 0x0201   
int 0x13         
jc  ERR
jmp 0x1000:0x0000

ERR:
cli
hlt
jmp ERR

times 510 - ($ - $$) db 0
dw 0xAA55
