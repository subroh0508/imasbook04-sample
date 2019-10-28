const path = require('path');
const webpack = require('webpack');

const KotlinWebpackPlugin = require('@jetbrains/kotlin-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');

const BUILD_MODE = process.env.NODE_ENV || 'development';
const isProduction = BUILD_MODE === 'production';

module.exports = {
  mode: BUILD_MODE,
  entry: path.resolve(__dirname, 'index.js'),
  output: {
    path: path.resolve(__dirname, 'public'),
    publicPath: '/',
    filename: '[name].js',
  },
  resolve: {
    alias: {
      'kotlinx-html-js': 'kotlinx-html',
    },
    modules: ['build', 'node_modules'],
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        include: path.resolve(__dirname, 'build'),
        exclude: [
          /kotlin\.js$/,
        ],
      },
    ],
  },
  plugins: [
    new KotlinWebpackPlugin({
      src: path.resolve(__dirname, 'src'),
      output: 'build',
      optimize: isProduction,
      moduleName: 'bundlekt', // ビルド時のJSファイル名
      moduleKind: 'commonjs',
      librariesAutoLookup: true,
      verbose: true,
      sourceMap: !isProduction,
      packagesContents: [require('./package.json')],
    }),
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, 'index.template.html'),
      filename: path.resolve(__dirname, 'public/index.html'),
    }),
  ],
};
