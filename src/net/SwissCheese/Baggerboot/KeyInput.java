package net.SwissCheese.Baggerboot;

import org.lwjgl.input.Keyboard;

class KeyInput {
    
    //Check for any keys that are pressed.
    static void listen(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            Render3D.requestClose();
        }
    }
}
