ANT	= sh ./PreparePMD.sh ;env LC_ALL=ja_JP.UTF-8 ant
PMD_BIN = "pmd-bin
PMD_DIR = "pmd"
PMD_COMMAND = ./$(PMD_BIN)/bin/run.sh pmd -d ./ -f text -R ./$(PMD_DIR)/pmd-rules.xml -l java

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

zip:
	$(ANT) zip
