#! /usr/bin/env python
from __future__ import division
from nltk.corpus import wordnet as wn
import nltk, re, pprint, os

paragraph = """At eight o'clock on Thursday morning
... Arthur didn't feel very good. I love this day quite much."""

def ie_preprocess(document):
	sentences = nltk.sent_tokenize(document)
	sentences = [nltk.word_tokenize(sent) for sent in sentences]
	sentences = [nltk.pos_tag(sent) for sent in sentences]
	return sentences

grammar = "NP: {<DT>?<JJ.*>*<NN.*>+}"
cp = nltk.RegexpParser(grammar)


stopwords = nltk.corpus.stopwords.words('english')
for w in stopwords:
	print w

#lens = ie_preprocess(paragraph)
#for l in lens:
#	print l
	#print cp.parse(l)

#read local file
#print os.listdir('.')
#f = open('document.txt')
#raw = f.read()

#for line in open("file.txt"):
#	for word in line.split():
#		if word.endswith('ing'):
#			print word


#for synset in list(wn.all_synsets('n')):
#	print synset

#tokens = nltk.word_tokenize(sentence)

#print(tokens)
print(1)