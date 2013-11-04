package denoflionsx.denLib.Mod.Handlers.NewFluidHandler;

import net.minecraftforge.fluids.FluidStack;

public interface IDenLibFluidHandler {

    // Return null if you want all events.
    public String lookingForFluid();

    public void onEvent(FluidStack fluid);
}
