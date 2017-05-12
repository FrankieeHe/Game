Typically you need to do 5 things to include a library in your project:

1) Add #include statements necessary files with declarations/interfaces, e.g.:

#include "library.h"
2) Add an include directory for the compiler to look into

-> Configuration Properties/VC++ Directories/Include Directories (click and edit, add a new entry)

3) Add a library directory for *.lib files:

-> project(on top bar)/properties/Configuration Properties/VC++ Directories/Library Directories (click and edit, add a new entry)

4) Link the lib's *.lib files

-> Configuration Properties/Linker/Input/Additional Dependencies (e.g.: library.lib;

5) Place *.dll files either:

-> in the directory you'll be opening your final executable from or into Windows/system32


Include files typically contain the declaration of a symbol (a function, a variable).
This let's the compiler know that a name is defined (in the header) or elsewhere (in the case of a declaration):

There exist header only libraries however that have their declarations and definitions code in the same file.

The include directories are the places where the compiler searches to resolve an #include "a.h" preprocessor directive.
But there are also library directories where the linker searches for needed libraries that usually provide implementations 
(definitions) to the declarations in your headers.

.lib files are precompiled libraries. if you include a .lib, you also need to include the .h/hpp header files, 
so your compiler knows how to access the functions in the .lib.
when you compile your program, all the functions used from the lib are only linked, they are note compiled again
//------------------------------------------------------------------------------------------------------------------
