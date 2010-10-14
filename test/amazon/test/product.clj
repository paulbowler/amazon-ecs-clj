(ns amazon.test.product
  (:use [amazon.product] :reload)
  (:use [clojure.test])
  (require [clj-http.client :as client]))

(deftest get-amazon-url-test
	(let
		[
			domain "ecs.amazonaws.com"
			access-key "1HWX1AM5RYHK7ZYTBZ82"
			secret-key "MQ+RXlRIMaGGwLUjHO18C9lqH6ZeFz4oSlMus25Z"
			associate-id "quote8-21"
		]
		(testing "Simple item lookup"
			(let
				[
					result
						(client/get 
							(get-amazon-url domain access-key secret-key associate-id
								{:Service "AWSECommerceService", :Operation "ItemLookup", :ItemId "0679722769"}
							)
						)
				]
				(is	(= 200 (:status result)))
			)
		)
		(testing "Book title search"
			(is (= 200 (:status (client/get (get-amazon-url domain access-key secret-key associate-id
				{:Service "AWSECommerceService", :Operation "ItemSearch", :SearchIndex "Books" :Title "Moab is my washpot"}))))
			)
		)
		(testing "Book title and author search"
			(is (= 200 (:status (client/get (get-amazon-url domain access-key secret-key associate-id
				{:Service "AWSECommerceService", :Operation "ItemSearch", :ResponseGroup "Small,Images,Similarities", :SearchIndex "Books" :Title "Moab is my washpot", :Author "Stephen Fry"}))))
			)
		)
	)
)