package dmpro.items;

public class CoinItem extends Item {
	
	public enum CoinType {
		GOLD(1.0f, "Gold"),
		PLATINUM(5.0f, "Platinum"),
		SILVER(.05f, "Silver"),
		COPPER(.005f, "Copper"),
		ELECTRUM(.5f, "Electrum");
		
		private float goldValue;
		private String coinName;
		
		CoinType(float goldValue, String coinName) {
			this.goldValue = goldValue;
			this.coinName = coinName;
		}
		
		public float getGoldValue() {
			return this.goldValue;
		}
		
		public String getCoinName() {
			return this.coinName;
		}
	}
	
	private CoinType coinType;
	
	public CoinItem(CoinType coinType, int amount) {
		this.itemType = ItemType.COINS;
		this.coinType = coinType;
		this.itemName = coinType.getCoinName();
		this.itemCount = amount;
		this.itemValue = Math.round(amount * coinType.getGoldValue());
		this.weight = amount;
	}

}
