var y = 20;
var z = "dgksd fasd askdf asdfjksdf sdkf j";
var q = 21;

//The following line includes another file's code
include "./testFolder/testInclude.cr"; //Includes testInclude.cr;
//Test comment

var x = @q;

Print(x);
Print(~x);
~x = 5;
doesNotExistAnywhere();
Print(x);
Print(q);