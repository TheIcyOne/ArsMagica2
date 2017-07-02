package am2.items;

import am2.api.IBoundItem;
import am2.defs.ItemDefs;
import am2.utils.SpellUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBoundHoe extends ItemHoe implements IBoundItem {

	public ItemBoundHoe() {
		super(ItemDefs.BOUND);
		this.maxStackSize = 1;
		this.setMaxDamage(0);
		this.setCreativeTab(null);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (!stack.hasTagCompound())
			return stack;
		ItemStack copiedStack = SpellUtils.merge(stack.copy());
		copiedStack.getTagCompound().getCompoundTag("AM2").setInteger("CurrentGroup", SpellUtils.currentStage(stack) + 1);
		ItemStack spell = new ItemStack(ItemDefs.spell);
		spell.setTagCompound(copiedStack.getTagCompound());
		spell.getTagCompound().getCompoundTag("AM2").setInteger("CurrentGroup", SpellUtils.currentStage(stack) + 1);
		copiedStack = spell.copy();
		SpellUtils.applyStackStage(copiedStack, entityLiving, null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, null, worldIn, true, true, 0);
		return stack;
	}

	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		NBTTagCompound nbt = item.getTagCompound();
		item = new ItemStack(ItemDefs.spell);
		item.setTagCompound(nbt);
		return false;
	}

	@Override
	public float maintainCost(EntityPlayer player, ItemStack stack) {
		return normalMaintain;
	}

	public ItemHoe registerAndName(String name) {
		this.setUnlocalizedName(new ResourceLocation("arsmagica2", name).toString());
		GameRegistry.register(this, new ResourceLocation("arsmagica2", name));
		return this;
	}

}
