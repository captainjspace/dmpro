/**
 * 
 */
package dmpro.data.loaders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dmpro.character.classes.CharacterClassType;
import dmpro.items.WeaponItem.WeaponType;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Nov 4, 2016
 */
public class ClassWeaponProficiencyRecord implements TSVData {
	
	public static int fieldCount = 2;
	CharacterClassType characterClassType;
	List<WeaponType> listWeaponTypes;
	
	
	public ClassWeaponProficiencyRecord (String [] fields) {
		characterClassType = CharacterClassType.valueOf(fields[0]);
		listWeaponTypes =  Arrays.asList(fields[1].split(",")).stream().map(p -> WeaponType.valueOf(p)).collect(Collectors.toList());
		
	}

	/**
	 * @return the characterClassType
	 */
	public CharacterClassType getCharacterClassType() {
		return characterClassType;
	}
	/**
	 * @param characterClassType the characterClassType to set
	 */
	public void setCharacterClassType(CharacterClassType characterClassType) {
		this.characterClassType = characterClassType;
	}
	/**
	 * @return the listWeaponTypes
	 */
	public List<WeaponType> getListWeaponTypes() {
		return listWeaponTypes;
	}
	/**
	 * @param listWeaponTypes the listWeaponTypes to set
	 */
	public void setListWeaponTypes(List<WeaponType> listWeaponTypes) {
		this.listWeaponTypes = listWeaponTypes;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((characterClassType == null) ? 0 : characterClassType.hashCode());
		result = prime * result + ((listWeaponTypes == null) ? 0 : listWeaponTypes.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ClassWeaponProficiencyRecord)) {
			return false;
		}
		ClassWeaponProficiencyRecord other = (ClassWeaponProficiencyRecord) obj;
		if (characterClassType != other.characterClassType) {
			return false;
		}
		if (listWeaponTypes == null) {
			if (other.listWeaponTypes != null) {
				return false;
			}
		} else if (!listWeaponTypes.equals(other.listWeaponTypes)) {
			return false;
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClassWeaponProficiencyRecord [" + (characterClassType != null ? "characterClassType=" + characterClassType + ", " : "")
				+ (listWeaponTypes != null ? "listWeaponTypes=" + listWeaponTypes : "") + "]";
	}
	

}
