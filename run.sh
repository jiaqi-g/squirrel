

libpath="./lib/boilerpipe-1.2.0.jar:"
libpath=$libpath"./lib/boilerpipe-sources-1.2.0.jar:"
libpath=$libpath"./lib/cli-1.0.0.jar:"
libpath=$libpath"./lib/gson-2.2.4.jar:"
libpath=$libpath"./lib/htmlcleaner-2.8.jar:"
libpath=$libpath"./lib/jsoup-1.7.3-sources.jar:"
libpath=$libpath"./lib/jsoup-1.7.3.jar:"
libpath=$libpath"./lib/lucene-analyzers-common-4.8.0.jar:"
libpath=$libpath"./lib/lucene-core-4.8.0.jar:"
libpath=$libpath"./lib/lucene-queryparser-4.8.0.jar:"
libpath=$libpath"./lib/mysql-connector-java-5.1.6-bin.jar:"
libpath=$libpath"./lib/nekohtml-1.9.13.jar:"
libpath=$libpath"./lib/sml-toolkit-0.7.1.jar:"
libpath=$libpath"./lib/sqljdbc4.jar:"
libpath=$libpath"./lib/xerces-2.9.1.jar:"

#DATABASE cs249 create
#related file: ./content/hotelReview, ./content/review_sent, ./content/sent_cnt, ./content/scores
#java -classpath $libpath./classes/Crawler-1-0-0-alpha.jar ucla.lucene.hotelReviewCreate
#java -classpath $libpath./classes/Crawler-1-0-0-alpha.jar ucla.lucene.reviewSentCreate
#java -classpath $libpath./classes/Crawler-1-0-0-alpha.jar ucla.lucene.sentCntCreate
#java -classpath $libpath./classes/Crawler-1-0-0-alpha.jar ucla.lucene.wordSimCreate

#LUCENE build index
#java -classpath $libpath./classes/Crawler-1-0-0-alpha.jar ucla.lucene.BuildIndex
#LUCENE search
#java -classpath $libpath./classes/Crawler-1-0-0-alpha.jar ucla.lucene.SearchFiles

#squirrel search
#call function in common.Database
#getRankedSentenceScores(String noun, String adj, Integer hotelId, Integer topK, Double simTh)
 
