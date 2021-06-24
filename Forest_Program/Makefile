ANT	= sh ./PreparePMD.sh ;env LC_ALL=ja_JP.UTF-8 ant
ARCHIVE	= $(shell basename `pwd`)
PMD_BIN = "pmd-bin"
PMD_DIR = "pmd"
PMD_COMMAND = ./$(PMD_BIN)/bin/run.sh pmd -d ./ -f textcolor -R ./$(PMD_DIR)/pmd-rules.xml -l java

all:
	$(ANT) all

clean:
	$(ANT) clean

test:
	$(ANT) test

pmd:all
	@echo $(PMD_COMMAND)
	@eval $(PMD_COMMAND) 2> /dev/null

tree: clean
	$(ANT) tree

forest: clean
	$(ANT) forest

semilattice: clean
	$(ANT) semilattice

install:
	$(ANT) install

doc:
	$(ANT) doc

wipe: clean
	@find . -name ".DS_Store" -exec echo rm -f {} ";" -exec rm -f {} ";"
	@find . -maxdepth 1 -name "$(PMD_BIN)" -exec echo rm -f {} ";" -exec rm -f {} ";"
	@find $(PMD_DIR) -maxdepth 1 -type d -name "$(PMD_BIN)-[0-9]*.[0-9]*.[0-9]*" -exec echo rm -rf {} ";" -exec rm -rf {} ";"
	@find .. -maxdepth 1 -name "$(ARCHIVE).zip" -exec echo rm -f {} ";" -exec rm -f {} ";"

zip:
	$(ANT) zip

lint: pmd
	@: