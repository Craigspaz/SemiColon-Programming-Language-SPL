var y = 20;
var z = "dgksd fasd askdf asdfjksdf sdkf j";
var q = 21;

//The following line includes another file's code
include "./testFolder/testInclude.cr"; //Includes testInclude.cr;
//Test comment

var x = @q;
var kd;

Print(x);
Print(~x);
~x = 5;
doesNotExistAnywhere();
Print(x);
Print(q);
q = 27;
Print("TEST");
Print(q[1]);

// Declare array
var arr = {};
var arrWithStuff = {5,3,2};
Print(arrWithStuff[1]);
arrWithStuff[1] = 8;
Print(arrWithStuff[1]);
append(arr,1);
Print(arr[0]);
append(arr,"Hello This is in an array");
Print(arr[1]);