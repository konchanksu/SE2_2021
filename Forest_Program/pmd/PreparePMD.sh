#!/bin/sh

PMD="pmd-bin"
PMDVersion="${PMD}-6.35.0"
PMDArchive="./PMDArchives/${PMDVersion}.zip"
PMDDirectory="./${PMDVersion}/"

if [ -e ${PMDArchive} ]
then
	if [ ! -e ${PMDDirectory} ]
	then
		unzip ${PMDArchive}
		ln -s ${PMDDirectory} ${PMD}
	else
		if [ ! -d ${PMDDirectory} ]
		then
			rm -rf ${PMDDirectory}
			unzip ${PMDArchive}
			ln -s ${PMDDirectory} ${PMD}
		fi
	fi
fi

exit 0
