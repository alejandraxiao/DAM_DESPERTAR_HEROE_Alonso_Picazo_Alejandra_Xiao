<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">
    <html>
      <head>
        <title>Ranking de jugadores</title>
      </head>
      <body style="background-color: #FADADD; font-family: 'Times New Roman';">

        <h2 style="text-align: center; color: #E75480; font-size: 24px;">Ranking de jugadores</h2>

        <table style="margin: auto; border-collapse: collapse; border: 3px solid #FF1493;">
          <tr style="background-color: white;">
            <th style="border: 3px solid #FF1493; padding: 8px 20px; text-align: center; color: black;">Posición</th>
            <th style="border: 3px solid #FF1493; padding: 8px 20px; text-align: center; color: black;">Nombre</th>
            <th style="border: 3px solid #FF1493; padding: 8px 20px; text-align: center; color: black;">Puntos</th>
          </tr>
          <xsl:for-each select="score/jugadores/jugador">
            <xsl:sort select="puntos" data-type="number" order="descending"/>
            <tr>
              <xsl:attribute name="style">
                <xsl:choose>
                  <xsl:when test="position() = 1">background-color: gold;</xsl:when>
                  <xsl:when test="position() mod 2 = 0">background-color: #FFC0CB;</xsl:when>
                  <xsl:otherwise>background-color: white;</xsl:otherwise>
                </xsl:choose>
              </xsl:attribute>
              <td style="border: 3px solid #FF1493; padding: 8px 20px; text-align: center; color: black;"><xsl:value-of select="position()"/></td>
              <td style="border: 3px solid #FF1493; padding: 8px 20px; text-align: center; color: black;"><xsl:value-of select="nombre"/></td>
              <td style="border: 3px solid #FF1493; padding: 8px 20px; text-align: center; color: black;"><xsl:value-of select="puntos"/></td>
            </tr>
          </xsl:for-each>
        </table>

      </body>
    </html>
  </xsl:template>

</xsl:stylesheet>