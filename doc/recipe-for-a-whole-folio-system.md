git clone okapi
mvn install
git clone raml-module-builder
mvn installgit clone mod-circulation
mvn installgit clone stripes-experiments
git checkout okapi-proxy-apicd stripes-connect
npm install
cd ..
cd stripes-core
npm install
cd node_modules/stripes-loader
npm install
npm run build
cd ../..
ln -s ../.. node_modules/@stripes-experiments  #(see notes regarding OS'es without symlink)
npm startConfigure/deploy the circulations module, create tenant and enable the module for the tenant
by these curl requests:curl  -X POST -D - -H "Content-type: application/json"  http://localhost:9130/_/deployment/modules -d '{
 "srvcId" : "module-id",
 "descriptor" : {
   "exec" : "java -jar C://indexdata/gitprojects/mod-circulation/target/circulation-fat.jar -Dhttp.port=%p"
  }
}'curl -D - -X POST -H "Accept: application/json" -H "Content-Type: application/json"  http://localhost:9130/_/proxy/modules -d '{
 "id" : "module-id",
 "name" : "patrons",
 "provides" : [ { "id" : "prov-id", "version": "1.2.3" }],
  "routingEntries" : [ {
     "methods" : [ "GET", "POST", "PUT", "DELETE" ],
     "path" : "/apis",
     "level" : "30",
     "type" : "request-response"
   } ]
}'curl -D - -X POST  -H "Content-Type: application/json" http://localhost:9130/_/proxy/tenants -d '{
 "id" : "tenant-id",
 "name" : "T1",
 "description" : "Tenant 1"
}'curl  -X POST  -H "Content-Type: application/json"  http://localhost:9130/_/proxy/tenants/tenant-id/modules -d '{
 "id" : "module-id"
}'Possibly also post a sample patron:curl -X POST -H "X-Okapi-Tenant: tenant-id" \
    -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: x" \
http://localhost:9130/apis/patrons -d '{
  "status" : "[status]",
  "patron_name" : "Patron 2",
  "contact_info" : {  },
  "total_loans": 2,
  "total_fines" : 2,
  "patron_code" : {  },
  "total_fines_paid" : "fines paid",
  "patron_barcode" : "bar code 2",
  "patron_local_id" : "local id 2"
}'start console at http://localhost:3000/ , go to Patrons tab.
