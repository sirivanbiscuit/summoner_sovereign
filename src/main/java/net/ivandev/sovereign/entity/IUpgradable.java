package net.ivandev.sovereign.entity;

public interface IUpgradable {

	/**
	 * Should return a texture path based on the level of the given entity. For
	 * example, capital cornerstones have the same model for each upgrade but the
	 * texture changes with each. If there aren't texture changes necessary then
	 * return the default texture path.
	 * 
	 * @return the texture path of the current entity level
	 */
	public String getLevelTexture();

	/**
	 * The minimum level of this object.
	 * 
	 * @return the minimum level
	 */
	public int minLevel();

	/**
	 * The maximum level of this object.
	 * 
	 * @return the maximum level
	 */
	public int maxLevel();

	/**
	 * The current level of this object.
	 * 
	 * @return the current level
	 */
	public int getUpgradeLevel();

	/**
	 * Adds the amount given by <code>factor</code> to the current level. This
	 * method does NOT verify level bounds on its own (methods
	 * <code>upgrade()</code> and <code>downgrade()</code> do, however). Preferabbly
	 * one should have a synced NBT data supplier here, unless they don't need
	 * levels to persist.
	 * 
	 * @param factor the level amount to add/subtract from the current level
	 */
	public void changeLevelByFactor(int factor);

	public default void upgrade() {
		if (this.getUpgradeLevel() < this.maxLevel()) {
			this.changeLevelByFactor(1);
		}
	}

	public default void downgrade() {
		if (this.getUpgradeLevel() > this.minLevel()) {
			this.changeLevelByFactor(-1);
		}
	}

}
