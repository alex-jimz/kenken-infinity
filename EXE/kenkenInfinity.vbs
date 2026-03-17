Set objShell = CreateObject("WScript.Shell")
Set objFSO = CreateObject("Scripting.FileSystemObject")

' Get the directory of the VBS script
scriptPath = WScript.ScriptFullName
scriptDir = objFSO.GetParentFolderName(scriptPath)

' Set the current directory to the script's directory
objShell.CurrentDirectory = scriptDir

' Run the batch file using a relative path
objShell.Run "kenkenInfinity.bat", 0
