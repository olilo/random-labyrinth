/**
 * 
 */
package utilities;

import java.awt.Graphics;

/**
 * @author OlilO,
 * created on 26.11.2006
 *
 */
public interface IMap {
	
	public boolean containsField(IField f);
	public void putField(IField f);
	public void removeField(IField f);
	public void paint(Graphics g);
}
