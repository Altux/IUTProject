strZipFile = "driver.zip"     'name of zip file

pwd = Replace(WScript.ScriptFullName, WScript.ScriptName, "")

Set objShell = CreateObject( "Shell.Application" )
Set objSource = objShell.NameSpace(pwd+strZipFile).Items()
Set objTarget = objShell.NameSpace(pwd)
intOptions = 256
objTarget.CopyHere objSource, intOptions