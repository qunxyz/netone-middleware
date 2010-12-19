<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" encoding="GBK"></xsl:output>
	<xsl:template match="/*"> 
		<xsl:apply-templates select="date" />
	</xsl:template> 
	<xsl:template match="date">
		<table> 
			<xsl:for-each select="item"> 
				<tr> 
					<td>
						<xsl:value-of select="text"/>:<xsl:value-of select="value"/>
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
</xsl:stylesheet>
