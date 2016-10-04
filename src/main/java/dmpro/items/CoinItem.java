package dmpro.items;

public class CoinItem extends Item {
	
	public enum CoinType {
		
		GOLD(1.0f, "Gold", "gp"),
		PLATINUM(5.0f, "Platinum", "pp"),
		SILVER(.05f, "Silver", "sp"),
		COPPER(.005f, "Copper", "cp"),
		ELECTRUM(.5f, "Electrum","ep");
		
		private float goldValue;
		private String coinName;
		private String shortName;
		
		CoinType(float goldValue, String coinName, String shortName) {
			this.goldValue = goldValue;
			this.coinName = coinName;
			this.shortName = shortName;
		}
		
		public CoinType getByShortName(String shortName) {
			CoinType returnValue = null;
			for (CoinType coinType: CoinType.values())
				if (coinType.getShortName().equals(shortName)) {
					returnValue = coinType;
					break;
				}
			if (returnValue != null) return returnValue;
			else throw new RuntimeException(shortName + " does not match any coin I know!");
			
		}
		
		public float getGoldValue() {
			return this.goldValue;
		}
		
		public String getCoinName() {
			return this.coinName;
		}

		/**
		 * @return the shortName
		 */
		public String getShortName() {
			return shortName;
		}
	}
	
	private CoinType coinType;
	
	public CoinItem(CoinType coinType, int amount) {
		this.itemType = ItemType.COINS;
		this.coinType = coinType;
		this.itemName = coinType.getCoinName();
		this.itemCount = amount;
		this.itemValue = Math.round(amount * coinType.getGoldValue());
		this.itemEncumbrance = amount;
	}

}
