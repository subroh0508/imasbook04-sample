# 765プロスケジュールチェッカー

 _このWebアプリは、週間IM@Study Vol.4収録「小鳥さんと一緒に100%Kotlinのサイト製作 〜Frontend編〜」のサンプルです。_
 
## How to Work

 https://imasbook04.otonashi-kotlin.dev/にて公開しています。
 
 ローカル環境の場合は、以下のコマンドで動作確認が可能です。
 
```
$ yarn run build   # index.htmlの生成に必要
$ yarn run dev

# productionモードでの動作確認
$ yarn run build
$ firebase serve --only hosting
```

## Directories

#### [src](https://github.com/subroh0508/imasbook04-sample/tree/master/src)
 Kotlin/JSのコードを配置。UIロジック + Firebase Functionsへのリクエストメソッドを記述。

#### [functions](https://github.com/subroh0508/imasbook04-sample/tree/master/functions)
 Google Calendarからプロデューサー・秋月律子・高木社長のスケジュールを取得するAPIを記述(Javascript)。Firebase Functions上で動作。

#### bundle
 [KotlinWebpackPlugin](https://github.com/JetBrains/create-react-kotlin-app/tree/master/packages/kotlin-webpack-plugin)から出力された、Kotlin→Javascriptへのトランスパイル後のファイルの配置場所。`yarn run build`を実行すると生成される。
 
#### public
 Webpackのビルドを通した最終的なバンドルファイル + `index.html`が出力される場所、兼Firebase Hostingで配信するファイルの配置場所。`yarn run build`を実行すると生成される。
