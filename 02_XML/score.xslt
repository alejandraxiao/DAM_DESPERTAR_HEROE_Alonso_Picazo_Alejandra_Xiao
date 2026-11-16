<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html" encoding="UTF-8"/>

  <xsl:template match="/">
    <html>
      <head>
        <meta charset="UTF-8"/>
        <title>Ranking de jugadores</title>
        <!-- Todo el diseño está en score.css -->
        <link rel="stylesheet" type="text/css" href="score.css"/>
      </head>
      <body>

        <h2>Ranking de jugadores</h2>

        <table>
          <tr>
            <th>Posición</th>
            <th>Nombre</th>
            <th>Puntos</th>
          </tr>
          <!-- Ordena los jugadores de mayor a menor puntos -->
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
