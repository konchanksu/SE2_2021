# Forest Program

ここには長期課題のプログラムがまとめられています。

## Makeコマンドについて

本プログラムではアプリケーションの実行、テストなどにMakeコマンドを使用しています。

以下が使用できるmakeコマンド一覧です。詳しくはマニュアルをご確認ください。

- `make all`

  - プログラムをコンパイルし、jarファイルを作成します。

- `make clean`

  - PMDのレポートのhtmlやjarファイルなどを削除します。

- `make doc`

  - このプログラムのJavaDocを作成し、ブラウザで開きます。

- `make forest`

  - サンプルの木構造データ`forest.txt`を読み込み、プログラムを実行します。

- `make install`

  - `make all`の内容を実行し、アプリケーションを作成します。

- `make jconsole`

  - `make test`の内容を実行し、性能解析のためのウィンドウを表示します。

- `make lint`

  - PMDを展開し、プログラムの静的解析を行います。
  デフォルトでは全てのプログラムに対して解析を行いますが、LINT_SRC=${プログラムのパス}を入力することで、特定のファイルに対して解析を行えます。

- `make pmd`

  - `make lint`と同様の内容です。

- `make pmd_html`

  - `make pmd`の内容を実行した後、解析結果をブラウザで開きます。

- `make semilattice`

  - サンプルの木構造データ`semilattice.txt`を読み込み、プログラムを実行します。

- `make tree`

  - サンプルの木構造データ`tree.txt`を読み込み、プログラムを実行します。

- `make wipe`

  - `make clean`の内容を実行した後、zipアーカイブと展開済みのPMDを削除します。

- `make zip`

  - 本アプリケーションのアーカイブを作成します。


