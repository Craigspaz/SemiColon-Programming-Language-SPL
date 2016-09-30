var y = 20;
var z = "dgksd fasd askdf asdfjksdf sdkf j";
var q = 21;

include "./testFolder/testInclude.cr";


var x = @q;

Print(x);
Print(~x);
~x = 5;
doesNotExistAnywhere();
Print(x);
Print(q);