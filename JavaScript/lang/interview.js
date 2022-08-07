let jsonStr = '[{"customer_id": "customer_1", "occured_at": "2022-05-12 09:00:10", "action": "follow", "brand_id": "brand_1"},{"customer_id": "customer_1", "occured_at": "2022-05-12 09:00:50", "action": "follow", "brand_id": "brand_2"},{"customer_id": "customer_1", "occured_at": "2022-05-12 09:00:30", "action": "unfollow", "brand_id": "brand_1"}]'

let jsonStr2 = `
[{"customer_id": "customer_1", "occured_at": "2022-05-12 09:00:10", "action": "follow", "brand_id": "brand_1"},
{"customer_id": "customer_1", "occured_at": "2022-05-12 09:00:50", "action": "follow", "brand_id": "brand_2"},
{"customer_id": "customer_1", "occured_at": "2022-05-12 09:00:30", "action": "unfollow", "brand_id": "brand_1"},
{"customer_id": "customer_2", "occured_at": "2022-05-12 09:00:40", "action": "follow", "brand_id": "brand_1"},
{"customer_id": "customer_2", "occured_at": "2022-05-12 09:00:10", "action": "follow", "brand_id": "brand_2"}]`

let customerList = JSON.parse(jsonStr2)

let record = {}
customerList.sort((first, second) => {
    let firstDate = Date.parse(first["occured_at"])
    let secondDate = Date.parse(second['occured_at'])
    return firstDate - secondDate
}).reduce((prev, cur) => {
    let currentCustomer = cur["customer_id"]
    let currentBrand = cur['brand_id']
    let action = cur['action']
    if (prev[currentCustomer]) {
        prev[currentCustomer][currentBrand] = action
    } else {
        prev[currentCustomer] = {

        }
        prev[currentCustomer][currentBrand] = action
    }
    return prev
}, record)
Object.keys(record).forEach((key) => {
   let followedBrands = Object.entries(record[key]).filter(([k,v]) => {
        return v === "follow"
    }).map(([k, v]) => k)
    record[key] = followedBrands
})
console.log(record)