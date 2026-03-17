@echo off
setlocal

rem Define the folder and the relative paths
set "folderPath=%~dp0"
set "targetPath=kenkenInfinity.vbs"
set "shortcutName=KenKen Infinity.lnk"
set "iconPath=Data\Icons\kenkenLogoUnitedAppEntranceNoTitle.ico"

rem Construct the PowerShell script as a multi-line string
set "psScript=powershell -NoProfile -Command " ^
    "$folderPath = '%folderPath:'='%';" ^
    "$targetPath = '%targetPath:'='%';" ^
    "$shortcutName = '%shortcutName:'='%';" ^
    "$iconPath = '%iconPath:'='%';" ^
    "$fullTargetPath = Join-Path -Path $folderPath -ChildPath $targetPath;" ^
    "$fullIconPath = Join-Path -Path $folderPath -ChildPath $iconPath;" ^
    "$shortcutPath = Join-Path -Path $folderPath -ChildPath $shortcutName;" ^
    "$WScriptShell = New-Object -ComObject WScript.Shell;" ^
    "$shortcut = $WScriptShell.CreateShortcut($shortcutPath);" ^
    "$shortcut.TargetPath = $fullTargetPath;" ^
    "$shortcut.WorkingDirectory = $folderPath;" ^
    "$shortcut.IconLocation = $fullIconPath;" ^
    "$shortcut.Save();"

rem Run the PowerShell script
%psScript%

endlocal