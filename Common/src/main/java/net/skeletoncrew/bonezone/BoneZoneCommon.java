package net.skeletoncrew.bonezone;

import net.darkhax.bookshelf.api.Services;

public class BoneZoneCommon {

    public final Content content;

    public BoneZoneCommon() {

        this.content = new Content();
        Services.REGISTRIES.loadContent(content);
    }
}