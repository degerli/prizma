package com.hrzafer.prizma.preprocessing;

import java.util.List;

/**
 *
 */
public abstract class ModifierFilter extends Filter {

    @Override
    public void filter(List<String> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (eval(token)) {
                tokens.set(i, modify(token));
            }
        }
    }
    
    protected abstract String modify(String token);
        
}
