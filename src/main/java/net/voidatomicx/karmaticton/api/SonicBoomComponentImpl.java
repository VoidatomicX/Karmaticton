package net.voidatomicx.karmaticton.api;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SonicBoomComponentImpl implements SonicBoomComponent {

    private int uses = 0;

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public void setUses(int uses) {
        this.uses = uses;
    }

    @Override
    public void readData(ValueInput valueInput) {

    }

    @Override
    public void writeData(ValueOutput valueOutput) {

    }
}