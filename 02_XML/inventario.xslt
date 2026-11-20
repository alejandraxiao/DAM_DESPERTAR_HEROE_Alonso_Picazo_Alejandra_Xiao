<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html" encoding="UTF-8"/>

  <xsl:template match="/">
    <html>
      <head>
        <meta charset="UTF-8"/>
        <title>Inventario del héroe</title>
        <!-- Mismo diseño que el ranking: todo está en score.css -->
        <link rel="stylesheet" type="text/css" href="score.css"/>
      </head>
      <body>

        <h2>Inventario del héroe</h2>

        <!-- Los atributos del inventario dicen cuántas pociones hay y cuántas caben -->
        <p>Pociones: <xsl:value-of select="inventario/@ocupadas"/>/<xsl:value-of select="inventario/@capacidad"/></p>

        <xsl:choose>
          <!-- Si no queda ninguna poción se muestra un mensaje en lugar de la tabla -->
          <xsl:when test="count(inventario/pocion) = 0">
            <p>El inventario está vacío</p>
          </xsl:when>
          <xsl:otherwise>
            <table>
              <tr>
                <th>Posición</th>
                <th>Objeto</th>
                <th>Curación</th>
              </tr>
              <xsl:for-each select="inventario/pocion">
                <tr>
                  <td><xsl:value-of select="@posicion"/></td>
                  <td><xsl:value-of select="nombre"/></td>
                  <td><xsl:value-of select="curacion"/></td>
                </tr>
              </xsl:for-each>
            </table>
          </xsl:otherwise>
        </xsl:choose>

      </body>
    </html>
  </xsl:template>

</xsl:stylesheet>
