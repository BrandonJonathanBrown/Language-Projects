@echo off
:: Comprehensive Cleanup and Repair Batch Script

:: Display a message
echo Starting comprehensive cleanup and repair...

:: Run as Administrator Check
if not "%1"=="am_admin" (
    echo Please run this script as Administrator.
    pause
    exit /b
)

:: Delete Temporary Files
echo Deleting Temporary Files...
del /q /f %TEMP%\* >nul 2>&1
del /q /f C:\Windows\Temp\* >nul 2>&1
echo Temporary Files Deleted.

:: Empty Recycle Bin
echo Emptying Recycle Bin...
rd /s /q C:\$Recycle.Bin >nul 2>&1
echo Recycle Bin Emptied.

:: Remove Old Windows Update Files
echo Removing Old Windows Update Files...
del /q /f C:\Windows\SoftwareDistribution\Download\* >nul 2>&1
echo Old Windows Update Files Removed.

:: Clear DNS Cache
echo Clearing DNS Cache...
ipconfig /flushdns
echo DNS Cache Cleared.

:: Run Disk Cleanup
echo Running Disk Cleanup...
cleanmgr /sagerun:1
echo Disk Cleanup Completed.

:: Check and Repair System Files
echo Checking and repairing system files with SFC...
sfc /scannow
echo SFC Scan Completed.

:: Repair Windows Image with DISM
echo Repairing Windows Image with DISM...
dism /online /cleanup-image /restorehealth
echo DISM Repair Completed.

:: Check Disk for Errors
echo Checking disk for errors...
chkdsk C: /f /r /x
echo Disk Check Completed. (Note: You might need to restart your computer for this to complete.)

:: Final Message
echo Cleanup and repair tasks are completed.
pause
