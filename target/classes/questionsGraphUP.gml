graph[
    node[
        id 1
        label "Which age group do you belong to?"
        display "radio"
    ]
    node[
        id 2
        label "exit"
        display "terminate"
    ]
    edge[
        source 1
        target 2
        label "less than 18 years old"
    ]
    edge[
        source 1
        target 2
        label "18 to 49 years old"
    ]
    edge[
        source 1
        target 2
        label "50 years or older"
    ]
  
]