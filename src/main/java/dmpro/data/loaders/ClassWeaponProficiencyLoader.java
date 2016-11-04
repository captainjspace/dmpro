/**
 * 
 */
package dmpro.data.loaders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import dmpro.character.classes.CharacterClass;
import dmpro.character.classes.Cleric;
import dmpro.character.classes.MagicUser;
import dmpro.items.WeaponItem.WeaponType;


/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Nov 4, 2016
 */
public class ClassWeaponProficiencyLoader extends TSVLoader {

	public ClassWeaponProficiencyLoader() {
		super(ClassWeaponProficiencyRecord.class, "class-weapon-proficiency.tsv");
	}

	public List<WeaponType> getAllowedWeaponTypes (Collection<CharacterClass> classes) {
		
		//For uniqueness
		HashSet<WeaponType> wt = new LinkedHashSet<WeaponType>();
		
		//List<WeaponType> wt = new ArrayList<WeaponType>();
		//new ArrayList<AttributeModifier>(new LinkedHashSet<AttributeModifier>(character.getStrength().getAttributeModifiers())));
		for (CharacterClass c : classes) {
			wt.addAll(
					this.tsvTable.stream().map(p -> (ClassWeaponProficiencyRecord) p)
					.filter( p -> p.getCharacterClassType() == c.getCharacterClassType())
					.findFirst().get().getListWeaponTypes()
					);
		}
		
		return new ArrayList<WeaponType>(wt);
	}
	
	
	public static void main (String[] args) {
		//test loader
		ClassWeaponProficiencyLoader cwpl = new ClassWeaponProficiencyLoader();
		System.out.println(cwpl.webTable);
		
		//test class filter
		CharacterClass cc1 = new Cleric();
		CharacterClass cc2 = new MagicUser();
		
		List<CharacterClass> lcc = new ArrayList<CharacterClass>();
		lcc.add(cc1); lcc.add(cc2);
		
		cwpl.getAllowedWeaponTypes(lcc).stream().forEach(p -> System.out.println(p.name()));
		
	}
}
