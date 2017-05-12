--out ref
key words for reference be used for parameter hand over
out and ref both as definition and using must be declared.
only ref var used do not have to be initialized.

--[dllimport ...]
  [DllImport("User32.dll")]
  [DllImport(Solver.Dll, CallingConvention = CallingConvention.Cdecl, EntryPoint = "mat s_std")
  specifying: using System.Runtime.InteropServices;
  Attribute Klasse
  Indicates that the attributed method is exposed by an unmanaged dynamic-link library (DLL) as a static entry point.
  for the using of dll interface a new userdefined interface must be defined.
  

--Open AssemblyInfo.cpp find the follwing part
[assembly: AssemblyTitleAttribute("Meta1")];
metadata is added to C++ code by enclosing declarations in squaer bracket []
the assembly: part at the start means that this is an attribute that applies to an assembly, as opposed to a type within
an assembly. There is a set of standard attributes that you can use to change the metadata compiled into a assembly, and
most of them rae listed in AssemblyInfo.cpp

Namespaces is a logical naming scheme for grouping related types.

The logical is seen or used by system/program and the physical seen or used by human. The .NET Framework uses a hierarchical naming scheme for grouping types into logical categories of related functionality, 
such as the ASP.NET technology or remoting functionality. Design tools can use namespaces to make it easier for developers to browse and reference types in their code. 
A single assembly can contain types whose hierarchical names have different namespace roots, and a logical namespace root can span multiple assemblies.
In the .NET Framework, a namespace is a logical design-time naming convenience, whereas an assembly establishes the name scope for types at run time.

 Remember that metadata is information that describes the types in an assembly, and it includes the fully qualified names of all the types

#using <mscorlib.dll>

It loads the DLL and reads the metadata for all the types that are defined there. Because mscorlib.dll contains most of the core .NET Framework classes,

The .NET Framework uses attributes for a variety of reasons and to address a number of issues. Attributes describe how to serialize data, specify characteristics that are used to enforce security, and limit optimizations by the just-in-time (JIT) compiler so the code remains easy to debug.
n C#, you specify an attribute by placing the name of the attribute, enclosed in square brackets ([]), above the declaration of the entity to which it applies.

EditorBrowsableAttribute gibt an, dass eine Eigenschaft oder Methode in einem Editor angezeigt werden kann. Diese Klass kann nicht vererbt werden.
Konstruktor: EditorBrowsableAttribute()
		EditorBrowsableAttribute(EditorBrowsableState)

ResourceManager-Klasse Stellt einen Ressourcen-Manager dar, der einfachen Zugriff auf kulturabhängige Ressourcen zur Laufzeit ermöglicht.
GetObject(String, CultureInfo)	
Ruft den Wert der angegebenen Ressource ab, die keine Zeichenfolge ist und für die angegebene Kultur lokalisiert wurde.

culture
Type: System.Globalization.CultureInfo
Die Kultur, für die die Ressource lokalisiert wurde. Wenn die Ressource für diese Kultur nicht lokalisiert ist, sucht der Ressourcen-Manager unter Verwendung von Fallback-Regeln nach einer geeigneten Ressource.
Wenn dieser Wert null ist, wird das CultureInfo-Objekt durch die CultureInfo.CurrentUICulture-Eigenschaft abgerufen.

STAThreadAttribute indicates that the COM threading model for the application is single-threaded apartment. This attribute must be present on the entry point of any application that uses Windows Forms; if it is omitted, the Windows components might not work correctly. If the attribute is not present, the application uses the multithreaded apartment model, which is not supported for Windows Forms.

 one of the things that COM does but .NET completely skips is providing threading guarantees for a class. A COM class can publish what kind of threading requirements it has. And the COM infrastructure makes sure those requirements are met.
 A thread that creates COM objects has to tell COM what kind of support it wants to give to COM classes that have restricted threading options. The vast majority of those classes only support so-called Apartment threading, their interface methods can only safely be called from the same thread that created the instance. In other words, they announce "I don't support threading whatsoever, please take care of never calling me from the wrong thread". Even if the client code actually does call it from another thread.
There's lots of code in Windows that requires an STA. Notable examples are the Clipboard, Drag + Drop and the shell dialogs (like OpenFileDialog). The UI thread of a WPF or Windows Forms project should always be STA, as does any thread that creates a window.
The promise you make to COM that your thread is STA however does require you to follow the single-thread apartment contract. They are pretty stiff and you can get pretty hard to diagnose trouble when you break the contract. Requirements are that you never block the thread for any amount of time and that you pump a message loop. The latter requirement is met by a WPF or Winforms' UI thread but you will need to take care of it yourself if you create your own STA thread. The common diagnostic for breaking the contract is deadlock.  

Zugriff auf Namespaces
Die meisten C# beginnen mit einem Abschnitt von using- Direktiven. In diesem Abschnitt werden die Namespaces
aufgelistet, due ub der Anwendung häufig verwendet werden. So muss der Programmierer nicht bei jeder Verwendung einer
Methdode aus einem dieser Namespaces den vollqualifizierten Namen angeben.

durch das Einfügen der Zeile: using System;
	an den Anfang eines Programms in der Folge den Code
	Console.WriteLine("Hello,World!");
anstelle dieses Codes verwenden:
	System.Console.WriteLine("Hello,World!");

Namespacealiase Die using-Direktive kann auch verwendet werden, um einen Alias für einen Namespace zu erstellen. Wenn
Sie zum Beispiel using Co = Company.Proj.Nested; // define an alias to represent a namespace

Verwenden von Namespaces zur Steuerung des Gültigkeitsbereichs
Das namespace-Schlüsselwort wird verwendet, um einen Gültigkeitsbereich zu deklarieren. Durch die Möglichkeit, Gültigkeitsbereiche innerhalb eines Projekts zu definieren, wird die Organisation des Codes unterstützt.
Zusätzlich lassen sich auf diese Art und Weise global eindeutige Typen erstellen.

Vollgekennzeichnete Namen
Namespaces und Typen verfügen über eindeutige Namen. Sie sind durch vollqualifizierte Namen beschrieben, die eine logische Hierarchie festlegen.

der vollqualifizierte Name ist als Kommentar angegeben, der auf die einzelnen Entitäten folgt.
namespace N1     // N1
{
    class C1      // N1.C1
    {
        class C2   // N1.C1.C2
        {
        }
    }
    namespace N2  // N1.N2
    {
        class C2   // N1.N2.C2
        {
        }
    }
}


Control.Visible-Eigenschaft
Ruft einen Wert ab, welcher angibt ob dieses- und die untergeordneten Steuerelemente angezeigt werden, oder legt diesen Wert fest.

System.Windows.Forms Namespace
Der System.Windows.Forms -Namespace enthält Klassen zum Erstellen von Windows-basierte Anwendung, die die umfangreiche Features der Benutzeroberfläche zur Verfügung, in das Microsoft Windows-Betriebssystem nutzen

