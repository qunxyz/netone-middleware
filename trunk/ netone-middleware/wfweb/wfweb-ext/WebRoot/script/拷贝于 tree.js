/**
* Copyright (C), 2007-2008, πÌ“’≈µµ€π§◊˜ “
* File Name: tree.js
* Encoding UTF-8
* Version: 1.0
* Date: Jun 2, 2007  2:04:37 AM
* History:    version 1.0
*/

var shrinkSign = "-";
var extendSign = "+";
Tree = Class.create();
Tree.prototype = {
    initialize : function(containerId)
    {
        this.container = $(containerId);
        this.branches = new Array();
    },
    add : function(branch)
    {
        var branchCount = this.branches.length;
        this.branches[branchCount] = branch;
    },
    write : function()
    {
        var treeStr = "";
        for(var i = 0;i<this.branches.length;i++) 
        {
            treeStr +=this.branches[i].write();
        }
        this.container.innerHTML = treeStr;
    }
};
Branch = Class.create();
Branch.prototype = {
    initialize : function(_id,_text)
    {
        this.id = _id;
        this.text = _text;
        this.leaves = new Array();
    },
    add : function(leaf)
    {
        var leafCount = this.leaves.length;
        this.leaves[leafCount] = leaf;
    },
    write : function()
    {
        var treeStr = "";
        var leavesCount = this.leaves.length;
        treeStr += "<span class='_leaf' onclick='showBranch(\""+this.id+"\")'>"
        if(leavesCount>0)
            treeStr += "<span id='T"+this.id+"'>"+extendSign+"</span>"+this.text +"</span>";
        else
            treeStr += this.text  +"</span>";
        treeStr += "<span id='"+this.id+"' class='_branch'>";
        for(var i=0;i<leavesCount;i++)
        {
            treeStr += this.leaves[i].write();
        }
        treeStr += "</span>";
        return treeStr;
    }
}
Leaf = Class.create();
Leaf.prototype = {
    initialize : function(_text,_link)
    {
        this.text = _text;
        this.link = _link;
    },
    write : function()
    {
        var treeStr = "<span class='_leaf'><a href='"+this.link+"'>"+this.text+"</a></span>"
        return treeStr;
    }
}
function showBranch(_id)
{
    var branch = $(_id);
    if(branch.style.display=="block")
    {
        branch.style.display="none";
    }
    else
    {
        branch.style.display="block";
    }
    changeSign(_id);
}
function changeSign(_id)
{
    var sign = "T"+_id;
    var sign_div = $(sign);
    if(sign_div.innerHTML.indexOf(extendSign)>-1)
        sign_div.innerHTML = shrinkSign;
    else
        sign_div.innerHTML = extendSign;
}
