@Echo off
javadoc -locale "ca_ES" -encoding UTF-8 -charset UTF-8 -d docs -classpath .;lib\TADs.jar -sourcepath src -use -author -version -private -windowtitle "ESTRUCTURA DE LA INFORMACIO" -doctitle "UOC. Estructura de la Informaci&oacute;<br>Pr&agrave;ctica" -header "<strong>TADs</strong>" -bottom "<a href=mailto:jalvarezc@uoc.edu>Submit a bug or feature</a>" -subpackages uoc.ded
pause
