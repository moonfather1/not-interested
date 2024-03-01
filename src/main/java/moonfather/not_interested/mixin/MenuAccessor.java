package moonfather.not_interested.mixin;

import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.village.Merchant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MerchantScreenHandler.class)
public interface MenuAccessor
{
    @Accessor(value = "merchant")
    Merchant getTraderField();
}
