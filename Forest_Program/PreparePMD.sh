#!/bin/sh

PMD="pmd-bin"
PMDDIR="pmd"
PMDVersion="${PMD}-6.35.0"
PMDArchive="./${PMDDIR}/PMDArchives/${PMDVersion}.zip"
PMDDirectory="./${PMDDIR}/${PMDVersion}/"

if [ -e ${PMDArchive} ]; then
	if [ ! -e ${PMDDirectory} ]; then
		unzip ${PMDArchive}
		ln -s ${PMDDirectory} ${PMD}
	else
		if [ ! -d ${PMDDirectory} ]; then
			rm -rf ${PMDDirectory}
			unzip ${PMDArchive}
			ln -s ${PMDDirectory} ${PMD}
		fi
	fi
fi

exit 0
