<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">
    <html>
      <body>
        <h2>Ranking de jugadores</h2>
        <table border="1">
          <tr>
            <th>Posición</th>
            <th>Nombre</th>
            <th>Puntos</th>
          </tr>
          <xsl:for-each select="score/jugadores/jugador">
            <xsl:sort select="puntos" data-type="number" order="descending"/>
            <tr>
              <td><xsl:value-of select="position()"/></td>
              <td><xsl:value-of select="nombre"/></td>
              <td><xsl:value-of select="puntos"/></td>
            </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>
  </xsl:template>

</xsl:stylesheet>