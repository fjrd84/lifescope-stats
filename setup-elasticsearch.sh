curl -X PUT \
       'http://localhost:9200/analysis/_mapping/query?update_all_types=true' \
       -H 'authorization: Basic ZWxhc3RpYzpjaGFuZ2VtZQ==' \
       -H 'cache-control: no-cache' \
       -d '{
         "properties": {
           "query": { 
             "type":     "text",
               "fielddata": true
           }
         }
       }'

curl -X PUT \
       'http://localhost:9200/analysis/_mapping/query?update_all_types=true' \
       -H 'authorization: Basic ZWxhc3RpYzpjaGFuZ2VtZQ==' \
       -H 'cache-control: no-cache' \
       -d '{
         "properties": {
           "analysis.solution": { 
             "type":     "text",
               "fielddata": true
           }
         }
       }'

curl -X PUT \
       'http://localhost:9200/analysis/_mapping/query?update_all_types=true' \
       -H 'authorization: Basic ZWxhc3RpYzpjaGFuZ2VtZQ==' \
       -H 'cache-control: no-cache' \
       -d '{
         "properties": {
           "analysis.problem": { 
             "type":     "text",
               "fielddata": true
           }
         }
       }'
