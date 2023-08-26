package net.skeletoncrew.bonezone.ui.bonecarving;

import net.minecraft.world.SimpleContainer;

import javax.annotation.Nullable;

public class BonecarverContainer extends SimpleContainer {

    private final BonecarverMenu owningMenu;

    @Nullable
    private Runnable changeListener;

    protected BonecarverContainer(BonecarverMenu owningMenu) {

        super(1);
        this.owningMenu = owningMenu;
    }

    public void setChangeListener(Runnable runnable) {

        this.changeListener = runnable;
    }

    @Override
    public void setChanged() {

        super.setChanged();
        this.owningMenu.slotsChanged(this);

        if (this.changeListener != null) {

            this.changeListener.run();
        }
    }
}
