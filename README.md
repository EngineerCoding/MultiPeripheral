MultiPeripheral
===============

This mod for MineCraft is mostly a framework for the mod [ComputerCraft](http://www.computercraft.info/). This will make it easier to create peripheral mods for ComputerCraft. That is not the only thing this does, it also allows to have multiple peripherals on one block. 

You might be wondering what that even means, so here is a brief explanation:

If mod A wants to create a peripheral on a dispenser then it simply is possible and acts like  normal. Now, you want to add mod B because that mod has awesome things that helps you, and it gives you more methods on blocks. Now there is an issue with plain ComputerCraft, onl mod A gets access to the block and get its peripheral on it. Now, what this mod/framework does is to literally melt those two together and mount it like it was a singular peripheral.

If you really wants to know how that works in code, this is the IPeripheral that gets returned: [the class](https://github.com/EngineerCoding/MultiPeripheral/blob/master/engineer/multiperipheral/wrapper/MultiIPeripheral.java).

What does this mean for the lua part? For that, you can look on the [forum post](http://www.computercraft.info/forums2/index.php?/topic/17309-multiperipheral-framework-for-computercraft/) about this mod, but eventually it is going to be moved to the wiki of this repository. I might even make  a website about it :)

If you find a bug please open an issue or try to fix it yourself with a pull request ;)

Oh, and Im going to add a few blocks and add default peripherals, but those are going to be highly configurable, so remove them if you want.