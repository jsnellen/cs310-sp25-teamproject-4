package edu.jsu.mcis.cs310.tas_sp25;
import java.util.zip.CRC32;

/**
 * Represents an employee badge, including a unique badge ID and a descriptive label.
 * 
 * <p>Each badge includes a unique ID and a descriptive label. The ID can be 
 * provided manually or generated automatically using a CRC32 checksum 
 * based on the description.</p>
 * 
 * @author Evan Ranjitkar
 */
public class Badge {

    private final String id, description;

    /**
     * Constructs a Badge with the specified ID and description.
     * 
     * @param id The badge ID.
     * @param description The employee's description.
     */
    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * Constructs a Badge using the specified description and automatically generates a badge ID
     * using a CRC23 checksum of the description.
     * 
     * @param description The employee's description
     */
    public Badge(String description) {
        this.description = description;
        this.id = generateBadgeId(description);
    }

    /**
     * Returns the badge ID.
     * 
     * @return The badge ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the employee's description.
     * 
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of the badge in the format: "#<ID> (<Description>)".
     * 
     * @return a formatted string representation of the badge. 
     */
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');

        return s.toString();

    }
    
    /**
     * Generates a badge ID using a CRC32 checksum of the given input string.
     * 
     * @param input The input string to generate a checksum from
     * @return the formatted badge ID as an 8-digit hexadecimal string.
     */
    private String generateBadgeId(String input) {
        CRC32 crc = new CRC32();
        crc.update(input.getBytes());
        long checksum = crc.getValue();
        return String.format("%08X", checksum);
    }

}
