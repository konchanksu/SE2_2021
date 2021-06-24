ANT	= sh ./PreparePMD.sh ;env LC_ALL=ja_JP.UTF-8 ant
ARCHIVE	= $(shell basename `pwd`)
LINT_SRC = "./" #PMDでのリントの実行対象。「make lint LINT_SRC=./forest/Example.java」などと指定できるように
PMD_BIN = "pmd-bin"
PMD_DIR = "pmd"
PMD_COMMAND = ./$(PMD_BIN)/bin/run.sh pmd -d $(LINT_SRC) -f textcolor -R ./$(PMD_DIR)/pmd-rules.xml -l java
PMD_COMMAND_HTML	= ./$(PMD_BIN)/bin/run.sh pmd -d ./ -f html -R ./$(PMD_DIR)/pmd-rules.xml -l java
PMD_REPORT_HTML	= pmd_report.html

all:
	$(ANT) all

clean:
	$(ANT) clean
	@find . -maxdepth 1 -name "$(PMD_REPORT_HTML)" -exec echo rm -f {} ";" -exec rm -f {} ";"

test:
	$(ANT) test

#PMDを実行
pmd:all
	@echo $(PMD_COMMAND)
	@eval $(PMD_COMMAND) 2> /dev/null

#PMDのHTML出力
pmd_html:all
	@echo $(PMD_COMMAND_HTML) '> ./$(PMD_REPORT_HTML)'
	-@eval $(PMD_COMMAND_HTML) > ./$(PMD_REPORT_HTML) 2> /dev/null
	open -a 'Safari' ./$(PMD_REPORT_HTML)

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