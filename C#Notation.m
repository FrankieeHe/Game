--out ref
key words for reference be used for parameter hand over
out and ref both as definition and using must be declared.
only ref var used do not have to be initialized.

--[dllimport ...]
  [DllImport("User32.dll")]
  [DllImport(Solver.Dll, CallingConvention = CallingConvention.Cdecl, EntryPoint = "mat_s_std")
  specifying: using System.Runtime.InteropServices;
  Attribute Klasse
  Indicates that the attributed method is exposed by an unmanaged dynamic-link library (DLL) as a static entry point.
  for the using of dll interface a new userdefined interface must be defined.
  

